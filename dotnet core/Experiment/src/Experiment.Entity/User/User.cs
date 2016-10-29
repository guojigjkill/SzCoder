namespace Experiment.Entity.User
{
    public class User : BaseEntity
    {
        //Id为用户手机号码

        public string Name { get; set; }
        public string MobileNo { get; set; }
        public string Password { get; set; }


        public User(string id, string password, string name, string mobileNo)
        {
            this.Id = id;
            this.Password = password;
            this.Name = name;
            this.MobileNo = mobileNo;
        }

        public User()
        {

        }
    }
}
