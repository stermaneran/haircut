import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from firebase_admin import auth
import re
import pyrebase
from requests import HTTPError


#  This function registers a new user with his hashed password
#  to the firebase database.
def register_user(email, password):
    valid_choice = True
    if len(password) < 6:
        print("\n[Error]: Password must be at least 6 digits")
        return

    if not re.match(r"[A-Za-z0-9\.\+_-]+@[A-Za-z0-9\._-]+\.[a-zA-Z]", email):
        print("\n[Error]: '{}' Not a valid email address".format(email))
        return

    if user_exists(email):
        print("\n[Error]: User '{}' already exists".format(email))
        return

    try:
        user = firebase.auth().create_user_with_email_and_password(email, password)
        uid = user['localId']
        ref = db.reference().child('Users').child(uid)
        ref.child("Photo").set(
            "https://firebasestorage.googleapis.com/v0/b/babershop-b43c6.appspot.com/o/profiles%2Fdefault.png?alt=media&token=9a5842a7-569e-4ca2-b0f8-6c5274933de4")
        ref.child('Email').set(email)
        ref.child('Password').set(password)
        ref.child('FirstName').set(input("Enter First Name: "))
        ref.child('LastName').set(input("Enter Last Name: "))
        ref.child('UserName').set(input("Enter User name: "))
        ref.child('Type').set("Non-Admin")
        while valid_choice:
            gender = input("Enter Gender (M/F): ")
            if gender == 'F' or gender == 'f':
                gender = "Female"
                valid_choice = False
            elif gender == 'M' or gender == 'm':
                gender = "Male"
                valid_choice = False
            else:
                print("Invalid choice!")
        ref.child('Gender').set(gender)
        ref.child('City').set(input("Enter City: "))
        ref.child('Street').set(input("Enter street: "))
        print("\n[Info]: User " + email + " Added")
    except (db.ApiCallError, db.TransactionError, HTTPError):
        print("\n[Info]: An error occurred")
    return


#  Remove a user given the User Name as the argument.
def remove_user(email):
    if not user_exists(email):
        print("\n[Error]: User '{}' not found".format(email))
        return

    try:
        users = db.reference('Users')
        for ch in users.get():
            testMail = users.child(ch).child("Email").get()
            if testMail == email:
                uid = ch
        auth.delete_user(uid)
        db.reference().child('Users').child(uid).delete()
        print("\n[Info]: User:" + email + " Deleted")
    except (db.ApiCallError, db.TransactionError):
        print("\n[Info]: An error occurred")


#  Determines whether a user exists in the database.
def user_exists(email):
    users = db.reference('Users')
    try:
        for ch in users.get():
            testMail = users.child(ch).child("Email").get()
            if testMail == email:
                return True
        return False
    except TypeError:
        return


#  Prints all of the system's users one by one.
def get_all_users():
    users = db.reference('Users')
    num = 1
    try:
        print("\n[Status]: Loading users data..")
        for ch in users.get():
            print("\nUser {}: ".format(num) + "\nFirst Name:" + users.child(ch).child("FirstName").get())
            print("Last Name:" + users.child(ch).child("LastName").get())
            print("User Name:" + users.child(ch).child("UserName").get())
            print("Email:" + users.child(ch).child("Email").get())
            print("Password:" + users.child(ch).child("Password").get())
            print("Gender:" + users.child(ch).child("Gender").get())
            print("City:" + users.child(ch).child("City").get())
            print("Street:" + users.child(ch).child("Street").get())
            print("Type:" + users.child(ch).child("Type").get())
            num += 1
        print("\n")
    except TypeError:
        print("\n[Info]: No registered users available")


#  Adds Photo to a user.
def add_Photo(email, path):
    users = db.reference('Users')
    for ch in users.get():
        testMail = users.child(ch).child("Email").get()
        if testMail == email:
            uid = ch
    print("\n[Status]: Uploading Profile picture..")
    auth = firebase.auth()
    user = auth.sign_in_with_email_and_password("admin@gmail.com", "admin12")
    storage = firebase.storage()
    try:
        ending = users.child(uid).child("Photo").get()[85 + len(uid):85 + len(uid) + 3]
        storage.child("profiles/" + uid + ending).put(path, user['idToken'])
        url = storage.child("profiles/" + uid + ending).get_url('BarberShop')
        users.child(uid).child("Photo").set(url)
        print("\n[Info]: User {} Profile picture has been successfully updated".format(email))
        return
    except (TypeError, FileNotFoundError):
        print("\n[Error]: No such file")


#  Clears the database.
#  CAREFUL! This function deletes all of the users. There is no going back!
def clear_users():
    try:
        users = db.reference('Users')
        for ch in users.get():
            auth.delete_user(ch)
        db.reference('Users').delete()
        print("\n[Info]: All users cleared from the system successfully!")
    except (db.ApiCallError, db.TransactionError):
        print("\n[Info]: An error occurred")
    except TypeError:
        print("\n[Info]: No registered users available")

    try:
        db.reference('Appointments').delete()
    except (db.ApiCallError, db.TransactionError):
        print("\n[Info]: An error occurred")
    except TypeError:
        print("\n[Info]: No registered Appointments")

#  Menu constants:
#  ---------------
REG_USR = '1'
DEL_USR = '2'
PRNT_USRS = '3'
CHECK_EXST = '4'
DEL_ALL_USRS = '5'
ADD_ITEM = '6'
EXIT = '7'


def menu():
    choice = 0
    while int(choice) not in range(1, 10):
        valid_choice = True
        print("\nPlease choose an operation:")
        print("1)  Register a new user to the system")
        print("2)  Delete an existing user")
        print("3)  Print All existing users")
        print("4)  Check whether a user exists")
        print("5)  Delete all users from the system")
        print("6)  Add Photo to a selected user")
        print("7)  Exit")

        while valid_choice:
            choice = input("\nAdmin@BarberShop:~$ ")
            if choice.isdigit():
                valid_choice = False
            else:
                print("Invalid choice!")

        print("\n")
    return choice


def main():
    print("""
+----------------------------------------------+
|             Hello and welcome to             |
|      'Shalom Barber Shop' Sefis Father"      |
|                    Server                    |
+----------------------------------------------+
    """)
    print("+--Menu--+")

    choice = menu()

    while choice != EXIT:

        if choice == REG_USR:
            Email = input("Enter Email: ")
            password = input("Enter Password: ")
            register_user(Email, password)

        elif choice == DEL_USR:
            Email = input("Enter Email: ")
            remove_user(Email)

        elif choice == PRNT_USRS:
            get_all_users()

        elif choice == CHECK_EXST:
            Email = input("Enter Email: ")
            if user_exists(Email):
                print("\n[Info]: The user exists!")
            else:
                print("\n[Info]: No such user!")

        elif choice == DEL_ALL_USRS:
            print("\n[WARNING]: this action will erase all data from Users database!")
            print("\nAre you sure you want to perform this action?")
            valid_choice = True
            while valid_choice:
                choice = input("[Y] Yes [N] No: ")
                if choice == 'Y' or choice == 'y' or choice == 'yes':
                    clear_users()
                    valid_choice = False
                elif choice == 'N' or choice == 'n' or choice == 'no':
                    valid_choice = False
                else:
                    print("Invalid choice!")

        elif choice == ADD_ITEM:
            email = input("Enter Email: ")
            if user_exists(email):
                path = input("Enter Photo Path: ")
                add_Photo(email, path)
            else:
                print("\n[Error]: User {} not found".format(email))

        choice = menu()

    return 0


if __name__ == '__main__':
    cred = credentials.Certificate("cred.json")
    app = firebase_admin.initialize_app(cred, {'databaseURL': "https://babershop-b43c6.firebaseio.com"})

    config = {
        "apiKey": "AIzaSyC7ewzQMpWLEXiVsiCYnXtq0ZKRIrzigDk",
        "authDomain": "babershop-b43c6.firebaseapp.com",
        "databaseURL": "https://babershop-b43c6.firebaseio.com",
        "storageBucket": "babershop-b43c6.appspot.com",
        "serviceAccount": "cred.json"
    }
    firebase = pyrebase.initialize_app(config)
    main()
