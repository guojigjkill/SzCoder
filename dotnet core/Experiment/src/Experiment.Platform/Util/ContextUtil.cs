namespace Experiment.Platform.Util
{
    using Context;

    public static class ContextUtil
    {
        public static IContext SERVICE_CONTEXT = new ServiceContext("Service", "127.0.0.1");
    }
}
