﻿namespace Experiment.Service
{
    using Cache;
    using DataAccess.Database.Base;
    using Entity;
    using Microsoft.Extensions.Logging;
    using Platform.Context;
    using Platform.Extension;
    using Platform.Monitor;
    using Platform.Util;
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Linq.Expressions;
    using System.Threading.Tasks;
    using Platform.Setting;

    public abstract class BaseService<T> : IService
        where T : IEntity
    {
        private IContext context;
        private IDbCollection<T> collection;
        private Repository<T> repository;

        private static ILogger _logger = LoggerUtil.CreateLogger<BaseService<T>>();

        private const string LAST_UPDATE = "LastUpdate";
        private const string LAST_UPDATED_BY = "LastUpdatedBy";

        private static ICache<T> cache;

        public BaseService(IContext context)
        {
            this.context = context;
            this.repository = Repository<T>.Instance;
            this.collection = repository.GetCollection();

            if(context.IsUseCache)
            {
                cache = LocalCache<T>.Instance;
                RebuildCache();
            }
        }

        public IContext GetContext()
        {
            return context;
        }

        public ILogger GetLogger()
        {
            return _logger;
        }

        private bool IsCacheReady()
        {
            if(cache == null)
            {
                return false;
            }

            return cache.IsReady();
        }

        private void RebuildCache()
        {
            if (cache != null)
            {
                var result = collection.GetEntitiesAsync().Result;
                cache.BuildWithList(result);
            }
        }

        private string GetPrefix(string prefix)
        {
            return string.Format("[{0}]_{1}", typeof(T).Name, prefix);
        }

        private string GetPrefix(IEnumerable<string> prefixes)
        {
            return string.Format("[{0}]_{1}", typeof(T).Name, string.Join(",", prefixes));
        }

        public T Get(string id)
        {
            using (new TimeMonitor(GetPrefix(id)))
            {
                try
                {
                    if (context.IsUseCache && IsCacheReady())
                    {
                        return cache.Get(id);
                    }

                    return collection.GetEntityByIdAsync(id).Result;
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return default(T);
                }
            }
        }

        //ToDo:非主键字段在数据库上建索引，直接从数据库获取
        public List<T> Get(Expression<Func<T, bool>> selector)
        {
            using (new TimeMonitor(GetPrefix(selector.ToString())))
            {
                try
                {
                    if (context.IsUseCache && IsCacheReady())
                    {
                        return cache.GetWithList().Where(selector.Compile()).ToList();
                    }

                    return collection.FindEntitiesAsync(selector).Result;
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return new List<T>();
                }
            }
        }

        public List<T> GetByIds(IEnumerable<string> ids)
        {
            using (new TimeMonitor(GetPrefix(ids)))
            {
                try
                {
                    if (ids.IsEmpty())
                    {
                        return new List<T>();
                    }

                    if (context.IsUseCache && IsCacheReady())
                    {
                        return cache.GetByIdsWithList(ids);
                    }

                    return Get(o => ids.Contains(o.Id));
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return new List<T>();
                }
            }
        }

        public void Iterate(Action<T> action)
        {
            IterateAsync(action).Wait();
        }

        private async Task IterateAsync(Action<T> action)
        {
            using (var cursor = await collection.FindAsync())
            {
                while (await cursor.MoveNextAsync())
                {
                    var batch = cursor.Current;
                    foreach (var document in batch)
                    {
                        // process document
                        action.Invoke(document);
                    }
                }
            }
        }

        public List<T> Get()
        {
            using (new TimeMonitor(GetPrefix(TimeMonitor.RAW)))
            {
                try
                {
                    if (context.IsUseCache && IsCacheReady())
                    {
                        return cache.GetWithList();
                    }

                    return collection.GetEntitiesAsync().Result;
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return new List<T>();
                }
            }
        }

        public List<string> GetIds()
        {
            using (new TimeMonitor(GetPrefix(TimeMonitor.RAW)))
            {
                try
                {
                    if (context.IsUseCache && IsCacheReady())
                    {
                        return cache.GetIds();
                    }

                    return collection.GetEntitiesAsync().Result.Select(o => o.Id).ToList();
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return new List<string>();
                }
            }
        }

        public virtual string Create(T entity)
        {
            using (new TimeMonitor(entity.Id))
            {
                try
                {
                    entity.Created = DateTime.Now;
                    entity.CreatedBy = context.UserId;

                    var id = collection.AddEntityAsync(entity).Result;

                    entity.Id = id;

                    if (context.IsUseCache && IsCacheReady())
                    {
                        cache.Set(entity);
                    }

                    LogInformation("Create", entity.Id);

                    return id;
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return string.Empty;
                }
            }
        }

        public virtual List<string> Create(List<T> entities)
        {
            using (new TimeMonitor(GetPrefix(entities.Select(o => o.Id).ToList())))
            {
                try
                {
                    foreach (var entity in entities)
                    {
                        entity.Created = DateTime.Now;
                        entity.CreatedBy = context.UserId;
                    }

                    var ids = collection.AddEntitiesAsync(entities).Result;

                    if(!entities.IsEmpty() && string.IsNullOrEmpty(entities[0].Id))
                    {
                        for (int i = 0; i < entities.Count; i++)
                        {
                            entities[i].Id = ids[i];
                        }
                    }

                    if (context.IsUseCache && IsCacheReady())
                    {
                        cache.Set(entities);
                    }

                    LogInformation("Create", string.Join(",", ids));

                    return ids;
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return new List<string>();
                }
            }
        }
        
        public virtual void Update(T entity)
        {
            using (new TimeMonitor(entity.Id))
            {
                try
                {
                    entity.LastUpdate = DateTime.Now;
                    entity.LastUpdatedBy = context.UserId;

                    if (context.IsUseCache && IsCacheReady())
                    {
                        cache.Set(entity);
                    }

                    LogInformation("Update", entity.Id);

                    var task = Task.Run(async () => { await collection.UpdateEntityAsync(entity); });
                    task.Wait();
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return;
                }
            }
        }

        public void Update<TField>(string id, string field, TField value)
        {
            using (new TimeMonitor(GetPrefix(id)))
            {
                try
                {
                    var task = Task.Run(
                        async () =>
                        {
                            await collection.UpdateEntityAsync(id, field, value);
                            await collection.UpdateEntityAsync(id, LAST_UPDATE, DateTime.Now);
                            await collection.UpdateEntityAsync(id, LAST_UPDATED_BY, context.UserId);
                        });

                    task.Wait();

                    if (context.IsUseCache && IsCacheReady())
                    {
                        var entity = collection.GetEntityByIdAsync(id).Result;
                        cache.Set(entity);
                    }

                    LogInformation("Update", id);
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return;
                }
            }
        }

        public virtual void Update(List<T> entities)
        {
            using (new TimeMonitor(GetPrefix(entities.Select(o => o.Id).ToList())))
            {
                try
                {
                    foreach (var entity in entities)
                    {
                        entity.LastUpdate = DateTime.Now;
                        entity.LastUpdatedBy = context.UserId;
                    }

                    var task = Task.Run(async () => { await collection.UpdateEntitiesAsync(entities); });
                    task.Wait();

                    if (context.IsUseCache && IsCacheReady())
                    {
                        cache.Set(entities);
                    }

                    LogInformation("Update", string.Join(",", entities.Select(o => o.Id).ToList()));
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return;
                }
            }
        }
        
        public virtual void Delete(string id)
        {
            using (new TimeMonitor(GetPrefix(id)))
            {
                try
                {
                    LogWarning("Delete", id);
                    var task = Task.Run(async () => { await collection.DeleteEntityAsync(id); });
                    task.Wait();

                    if (context.IsUseCache && IsCacheReady())
                    {
                        cache.Remove(id);
                    }
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return;
                }
            }
        }

        public virtual void Delete(List<string> ids)
        {
            using (new TimeMonitor(GetPrefix(ids)))
            {
                try
                {
                    LogWarning("Delete", string.Join(",", ids));
                    var task = Task.Run(async () => { await collection.DeleteEntitiesAsync(ids); });
                    task.Wait();

                    if (context.IsUseCache && IsCacheReady())
                    {
                        cache.Remove(ids);
                    }
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return;
                }
            }
        }

        public virtual void Delete(Expression<Func<T, bool>> selector)
        {
            using (new TimeMonitor(GetPrefix(selector.ToString())))
            {
                try
                {
                    LogWarning("Delete", selector.ToString());
                    var task = Task.Run(async () => { await collection.DeleteEntitiesAsync(selector); });
                    task.Wait();

                    if (context.IsUseCache && IsCacheReady())
                    {
                        var ids = cache.GetWithList().Where(selector.Compile()).Select(o => o.Id).ToList();
                        cache.Remove(ids);
                    }
                }
                catch (Exception ex)
                {
                    LogException(ex);
                    return;
                }
            }
        }

        public void LogInformation(string action, string id)
        {
            if (bool.Parse(Setting.Instance.Get("IsLogInformation")))
            {
                _logger.LogInformation(string.Format("Action:{0};Id:{1};UserId:{2};Ip:{3}", action, id, context.UserId, context.RemoteIp));
            }
        }

        public void LogWarning(string action, string id)
        {
            _logger.LogWarning(string.Format("Action:{0};Id:{1};UserId:{2};Ip:{3}", action, id, context.UserId, context.RemoteIp));
        }

        public void LogException(Exception ex)
        {
            _logger.LogError(string.Format("Message:{0};StackTrace:{1};UserId:{2};Ip:{3}", ex.Message, ex.StackTrace, context.UserId, context.RemoteIp));
        }
    }
}
