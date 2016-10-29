namespace Experiment.WebCore.Extension
{
    using Microsoft.AspNetCore.Builder;
    using Middleware;

    public static class UserTypeExtension
    {
        public static IApplicationBuilder UseUserType(this IApplicationBuilder builder)
        {
            return builder.UseMiddleware<UserTypeMiddleware>();
        }
    }
}
