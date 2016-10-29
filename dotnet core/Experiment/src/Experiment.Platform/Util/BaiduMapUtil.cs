namespace Experiment.Platform.Util
{
    using Model.BaiduMap;
    using Newtonsoft.Json;

    public static class BaiduMapUtil
    {
        public static GeocoderSearchResponse GetGeo(double latitude, double longitude)
        {
            var url = string.Format("http://api.map.baidu.com/geocoder/v2/?ak={0}&location={1},{2}&output=json&coordtype=wgs84ll", "test", latitude, longitude);
            return JsonConvert.DeserializeObject<GeocoderSearchResponse>(HttpUtil.GetString(url));
        }
    }
}
