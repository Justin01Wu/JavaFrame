package anotherpackage;
import scjp.testclass.*;

	public class SubclassY extends SuperclassX
	{
          SuperclassX objX = new SubclassY();
          SubclassY objY = new SubclassY();

		void subclassMethodY()
		{

			//objX.superclassMethodX(); // this is an error
                        objY.superclassMethodX();
			int i;
			//i = objX.superclassVarX;  // this is a compilation error
                        i = objY.superclassVarX;  // this is correct

                        // above error line because of those use calss
                        // not inherit class ,So protected modifier make no sense

                        // The following lines show protected modifier meaning
                        superclassMethodX();
                        i = superclassVarX;

                        //SuperclassX objC = new SuperclassX();
                        //objC.superclassMethodX();
                        //i=objC.
		}
	}
