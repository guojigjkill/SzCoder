namespace Experiment.WebCore.Base
{
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json;
    using Platform.Context;
    using Platform.Util;
    using Service;
    using System;
    using System.Collections.Generic;
    using Microsoft.AspNetCore.Mvc;
    using System.Security.Claims;
    using Microsoft.AspNetCore.Http;

    public abstract class BaseController : Controller
    {
        private IContext context;

        private Dictionary<string, IService> services = new Dictionary<string, IService>();

        private static ILogger _logger = LoggerUtil.CreateLogger<BaseController>();

        public IContext ServiceContext
        {
            get
            {
                if (context == null)
                {
                    if (User != null && User.FindFirst(ClaimTypes.NameIdentifier) != null)
                    {
                        context = new ServiceContext(User.FindFirst(ClaimTypes.NameIdentifier).Value, GetRemoteIp());
                    }
                    else
                    {
                        context = new ServiceContext(null, GetRemoteIp());
                    }
                }

                return context;
            }
        }

        public T GetService<T>()
            where T : IService
        {
            var type = typeof(T);

            if (!services.ContainsKey(type.Name))
            {
                IContext context = CreateServiceContext(type.Name);
                services.Add(type.Name, (T)Activator.CreateInstance(type, context));
            }

            return (T)services[type.Name];
        }

        public IContext CreateServiceContext(string serviceName)
        {
            return new ServiceContext(this.ServiceContext.UserId, this.ServiceContext.RemoteIp, false);
        }

        public string GetRemoteIp()
        {
            var remoteIp = HttpContext.Connection?.RemoteIpAddress?.ToString();

            if (!string.IsNullOrEmpty(remoteIp))
            {
                return remoteIp;
            }

            string[] remoteIpHeaders =
            {
                "X-FORWARDED-FOR",
                "REMOTE_ADDR",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "X_FORWARDED_FOR",
                "CLIENT_IP",
                "X_FORWARDED",
                "X_CLUSTER_CLIENT_IP",
                "FORWARDED_FOR",
                "FORWARDED"
            };

            string value;
            foreach (string remoteIpHeader in remoteIpHeaders)
            {
                if (HttpContext.Request.Headers.ContainsKey(remoteIpHeader))
                {
                    value = Request.Headers[remoteIpHeader];
                    if (!string.IsNullOrEmpty(value))
                    {
                        value = value.Split(',')[0].Split(';')[0];
                        if (value.Contains("="))
                        {
                            value = value.Split('=')[1];
                        }
                        value = value.Trim('"');
                        if (value.Contains(":"))
                        {
                            value = value.Substring(0, value.LastIndexOf(':'));
                        }
                        return value.TrimStart('[').TrimEnd(']');
                    }
                    else
                    {
                        break;
                    }
                }
            }

            return "no header found or empty value found";
        }

        public string GetSessionValue(string key)
        {
            byte[] value;
            HttpContext.Session.TryGetValue(key, out value);

            return StringUtil.ByteArrayToStr(value);
        }

        public void ClearSession()
        {
            HttpContext.Session.Clear();
        }

        public void RemoveSession(string key)
        {
            if (!string.IsNullOrEmpty(key))
            {
                HttpContext.Session.Remove(key);
            }
        }

        public void SetSessionValue(string key, string value)
        {
            if (!string.IsNullOrEmpty(value))
            {
                HttpContext.Session.SetString(key, value);
            }
        }

        public T GetSessionObject<T>(string key)
        {
            var value = HttpContext.Session.GetString(key);
            return value == null ? default(T) : JsonConvert.DeserializeObject<T>(value);
        }

        public void SetSessionObject(string key, object value)
        {
            if(value != null)
            {
                HttpContext.Session.SetString(key, JsonConvert.SerializeObject(value));
            }
        }
    }
}
