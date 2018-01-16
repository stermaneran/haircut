package com.example.user.eran;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class MockitoTest {

  String UserName;
  String Password;
  String PasswordWrong;
  String NonValidPassword;
  String fname ;
  String lname ;
  String email ;
  String pass1 ;
  String pass2 ;
  String street;
  String city ;
  String NonValidcity;
  registrationPage Act;

  //  Initialize of an activity and strings we use in the tests.
  @Before
  public void initialize() {
    Act = mock(registrationPage.class);
    Password = "123456";
    PasswordWrong = "11111111";
    NonValidPassword = "1235";       //  Not valid Password - to short, needed at least 6 charecters.
    fname ="shay";
    lname = "cohen";
    UserName = "shayc";
    email = "shay@gmail.com";
    pass1 = "111111";
    pass2 = "111111";
    street = "King-George";
    city = "tel aviv";
    NonValidcity = "";

  }

  //  Checks that the function returns false on no city input
  @Test
  public void shortUserLength() {
    when(Act.validateForm(fname,  lname,  UserName,  email,  pass1,  pass2,  street,  NonValidcity)).thenReturn(false);
    assertFalse(Act.validateForm(fname,  lname,  UserName,  email,  pass1,  pass2,  street,  NonValidcity));
  }

  //  Checks that the function returns true on a valid name, valid password and the varify of the password.
  @Test
  public void newUserLength() {
    when(Act.validateForm( fname,  lname,  UserName,  email,  pass1,  pass2,  street,  city)).thenReturn(true);
    assertTrue(Act.validateForm(fname,  lname,  UserName,  email,  pass1,  pass2,  street,  city));
  }

//    Checks that the function returns false on a short password.
  @Test
  public void shortPassLength() {
    when(Act.validateForm(fname,  lname,  UserName,  email,  NonValidPassword,  NonValidPassword,  street,  city)).thenReturn(false);
    assertFalse(Act.validateForm(fname,  lname,  UserName,  email,  NonValidPassword,  NonValidPassword,  street,  city));
  }
//    Checks that the function returns false on a wrong verify of the password.
  @Test
  public void inCorectPassword() {
    when(Act.validateForm(fname,  lname,  UserName,  email,  pass1,  PasswordWrong,  street,  city)).thenReturn(false);
    assertFalse(Act.validateForm(fname,  lname,  UserName,  email,  pass1,  PasswordWrong,  street,  city));
  }
}