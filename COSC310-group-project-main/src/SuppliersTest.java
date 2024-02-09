import org.junit.Test;

import static org.junit.Assert.*;

public class SuppliersTest extends DBConnection{
    @Test
    public void getSupplierNameByIdTest(){
        int supplier = Integer.parseInt(getSupplierNameById(1));
        assertEquals("CoolGear", supplier);
    }

    @Test
    public void getProductSupplierTest(){
        String productSupplier = getProductSupplier(2);
        assertEquals("CoolGear", productSupplier);
    }

}