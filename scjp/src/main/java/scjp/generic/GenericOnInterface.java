
// we don't have class FF, but it can compile
package scjp.generic;

import java.util.Collection;


public interface GenericOnInterface<FF> {
    
    //boolean add(E o);  // this can't be compiled
    
    
    boolean add(FF o);   // this can be compiled
    // The new notation says that the argument to add() 
    // is no longer any Object but must be compatible
    // with the type of the interface.
    
    
    boolean addAll(Collection<? extends FF> c);
    // The ? extends FF notation says
    //that the argument must be a generic collection 
    //whose type is compatible with FF. ? refers to the
    //type of the argument collection. 
    //Don’t be confused by the extends keyword; it doesn’t mean
    //that the argument Collection’s type must extend FF. 
    //It means the argument Collection’s type
    // must be compatible with FF, as determined by the 
    // instanceof operator. So if FF is a class, the
    //argument Collection’s type might be FF or any subclass of FF. 
    //If FF is an interface, the argument
    // Collection’s type might be FF, or any subinterface of FF    
}
