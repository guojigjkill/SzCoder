namespace Experiment.Service.User
{
    using Entity.User;
    using Platform.Context;
    using Result;

    public class UserService : BaseService<User>
    {
        private const string INVALID_USERNAME = "User name is invalid, please check it.";
        private const string INVALID_PWD = "Password is invalid, please check it.";
        private const string USER_ALREADY_EXIST = "User is already exist.";

        public UserService(IContext context) : base(context) { }

        public ServiceResult LogIn(string id, string pwd)
        {
            ServiceResult result = new ServiceResult();

            User user = Get(id);

            if (user == null)
            {
                result.IsSuccessful = false;
                result.Msg = INVALID_USERNAME;
            }
            else
            {
                if (user.Password.Equals(pwd))
                {
                    result.IsSuccessful = true;
                }
                else
                {
                    result.IsSuccessful = false;
                    result.Msg = INVALID_PWD;
                }
            }

            return result;
        }

        public ServiceResult SignUp(User user)
        {
            if (user == null || string.IsNullOrEmpty(user.Id) || string.IsNullOrEmpty(user.Password))
            {
                return new ServiceResult(false, "用户名或者密码不能为空");
            }

            ServiceResult result = new ServiceResult();

            User dbUser = Get(user.Id);

            if (dbUser == null)
            {
                Create(user);
                result.IsSuccessful = true;
            }
            else
            {
                result.IsSuccessful = false;
                result.Msg = USER_ALREADY_EXIST;
            }

            return result;
        }

        public bool IsUserExist(string id)
        {
            return Get(id) != null;
        }
    }
}
