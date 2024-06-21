package Pojo;


public class User {
						int id;
                       String firstName;
                       String lastName;
                       String email;
                       String password;
                       String phoneNo;
                       String userType;
                       
					public String getFirstName() {
						return firstName;
					}
					public int getId() {
						return id;
					}
					public void setId(int i) {
						id = i;
					}
					public String getUserType()
					{
						return userType;
					}
					public void setUserType(String ut) {
						this.userType = ut;
					}
					public void setFirstName(String firstName) {
						this.firstName = firstName;
					}
					public String getLastName() {
						return lastName;
					}
					public void setLastName(String lastName) {
						this.lastName = lastName;
					}
					public String getEmail() {
						return email;
					}
					public void setEmail(String email) {
						this.email = email;
					}
					public String getPassword() {
						return password;
					}
					public void setPassword(String password) {
						this.password = password;
					}
					public String getPhoneNo() {
						return phoneNo;
					}
					public void setPhoneNo(String phoneNo) {
						this.phoneNo = phoneNo;
					}
					public User(String firstName, String lastName, String email, String password, String phoneNo) {
						
						this.firstName = firstName;
						this.lastName = lastName;
						this.email = email;
						this.password = password;
						this.phoneNo = phoneNo;
					}
					public User(int id,String firstName, String lastName, String email, String password, String phoneNo,String ut) {
						 this.id = id;
						this.firstName = firstName;
						this.lastName = lastName;
						this.email = email;
						this.password = password;
						this.phoneNo = phoneNo;
						this.userType=ut;
					}
					public User() {}
					@Override
					public String toString() {
						return "User [firstName=" + firstName +" id = "+ getId() +", lastName=" + lastName + ", email=" + email
								+ ", password=" + password + ", phoneNo=" + phoneNo+" userType = "+userType + " , getFirstName()="
								+ getFirstName() + ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail()
								+ ", getPassword()=" + getPassword() + ", getPhoneNo()=" + getPhoneNo()
								+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
								+ super.toString() + "]";
					}
					
					
                       
                       
}

