# We keep the method because the function is usefull for us.
-keep class package com.example.user.eran.Haircut{
   public void build();
}

#_price is never directly refrenced but used
-keep class package com.example.user.eran.Appointment{
    String _price;
}
