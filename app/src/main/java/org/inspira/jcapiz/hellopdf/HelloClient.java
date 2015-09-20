package org.inspira.jcapiz.hellopdf;
/*
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
*/
//import HelloApp.*;

public class HelloClient
{
//  static Hello helloImpl;
/*
  public static String doCORBA(String args[])
    {
      String response = null;
      try{
        // create and initialize the ORB
        ORB orb = ORB.init(args,null);

        // get the root naming context
        org.omg.CORBA.Object objRef = 
            orb.resolve_initial_references("NameService");
        // Use NamingContextExt instead of NamingContext. This is 
        // part of the Interoperable naming Service.  
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
 
        // resolve the Object Reference in Naming
        String name = "Hello";
        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));

        response = ("Obtained a handle on server object: " + helloImpl);
        response = response.concat(helloImpl.sayHello());
        helloImpl.shutdown();

        } catch (Exception e) {
          response = ("ERROR : " + e) ;
          e.printStackTrace(System.out);
        }
      return response;
    }
*/
}
