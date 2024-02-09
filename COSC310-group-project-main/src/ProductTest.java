import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest extends DBConnection{
    @Test
    public void getProductPriceTest(){
        int price = (int) getProductPrice(1);
        assertEquals("669.99", price);
    }

    @Test
    public void getProductNameTest(){
        String price = getProductName(1);
        assertEquals("Skis", price);
    }

    @Test
    public void checkProductExistsTest(){
        boolean id = checkProductExists(1);
        assertEquals(true, id);
    }
}