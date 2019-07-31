import java.io.Serializable;

public class CarProduct implements Serializable {
     private String name;
     public CarProduct(String name)
     {
         this.name = name;
     }
     public String getName()
     {
         return this.name;
     }
     public void setName(String name)
     {
         this.name = name;
     }
}
