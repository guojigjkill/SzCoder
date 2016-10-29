namespace Experiment.WebCore.Base
{
    using Entity.User;
    using Microsoft.AspNetCore.Http;
    using Microsoft.AspNetCore.Mvc;
    using Platform.Util;
    using Service.User;
    using System.Collections.Generic;
    using System.Security.Claims;

    public abstract class BaseAccountController : BaseController
    {
        // GET: /<controller>/
        public IActionResult Login(string id, string password, string returnUrl)
        {
            if (!string.IsNullOrEmpty(id) && !string.IsNullOrWhiteSpace(id))
            {
                id = id.Trim();

                if (ModelState.IsValid)
                {
                    var userService = this.GetService<UserService>();
                    var result = userService.LogIn(id, password);

                    if (result.IsSuccessful)
                    {
                        LogIn(userService.Get(id));

                        // 如果是注册或者登陆页面，跳转到首页
                        if (returnUrl != null &&
                            (returnUrl.ToLower().Contains("account/login") ||
                            returnUrl.ToLower().Contains("account/signup")))
                        {
                            returnUrl = null;
                        }
                    }
                    else
                    {
                        ViewBag.ErrorMsg = result.Msg;
                        ViewBag.ReturnUrl = returnUrl;

                        return View();
                    }
                }
            }
            else
            {
                if (string.IsNullOrEmpty(returnUrl) || returnUrl.Equals("/"))
                {
                    returnUrl = HttpContext.Request.PathBase;
                }
                ViewBag.ReturnUrl = returnUrl;
                return View();
            }

            return Redirect(UrlUtil.GetRelativeUrl(returnUrl));
        }

        public IActionResult SignUp(User user, string returnUrl)
        {
            if (user != null && !string.IsNullOrEmpty(user.Id))
            {
                user.Id = user.Id.Trim();

                if (ModelState.IsValid)
                {
                    var result = this.GetService<UserService>().SignUp(user);

                    if (result.IsSuccessful)
                    {
                        LogIn(user);

                        //如果是注册或者登陆页面，跳转到首页
                        if (returnUrl != null &&
                            (returnUrl.ToLower().Contains("account/login") ||
                            returnUrl.ToLower().Contains("account/signup")))
                        {
                            returnUrl = null;
                        }
                    }
                    else
                    {
                        ViewBag.ErrorMsg = result.Msg;
                        ViewBag.ReturnUrl = returnUrl;
                        return View();
                    }
                }
            }
            else
            {
                if (string.IsNullOrEmpty(returnUrl) || returnUrl.Equals("/"))
                {
                    returnUrl = HttpContext.Request.PathBase;
                }
                ViewBag.ReturnUrl = returnUrl;
                return View();
            }

            return Redirect(UrlUtil.GetRelativeUrl(returnUrl));
        }


        private void LogIn(User user)
        {
            var claims = new List<Claim> { new Claim(ClaimTypes.NameIdentifier, user.Id) };

            var identity = new ClaimsIdentity(claims, "Password");
            HttpContext.Authentication.SignInAsync("Cookies", new ClaimsPrincipal(identity));
        }

        public IActionResult LogOff(string returnUrl)
        {
            HttpContext.Authentication.SignOutAsync("Cookies");
            return Redirect(UrlUtil.GetRelativeUrl(returnUrl));
        }
    }
}
