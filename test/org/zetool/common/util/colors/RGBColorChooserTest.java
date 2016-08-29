package org.zetool.common.util.colors;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class RGBColorChooserTest {

    @Test
    public void onlyRrbPanel() {
        RGBColorChooser c = new RGBColorChooser(Color.yellow);

        assertThat(c.getChooserPanels().length, is(equalTo(1)));
    }
    
//    @Test
//    public void returnedColorCorrect() throws InterruptedException {
//        Callable r = new Callable() {
//            @Override
//            public Object call() throws Exception {
//                System.out.println("In future 1");
//                RGBColorChooser.showDialog(null, "RGB", Color.yellow);
//                return null;
//            }
//        };
//        
//        List<Callable<Object>> futures = new LinkedList<>();
//        futures.add(r);
//        
//        ExecutorService e = Executors.newCachedThreadPool();
//        
//        Callable r2 = new Callable() {
//            final ExecutorService e1 = e;
//            
//            @Override
//            public Object call() throws Exception {
//                System.out.println("In future 2");
//                return null;
//            }
//            
//        };
//        
//        futures.add(r2);
//        
//        e.invokeAll(futures);
//    }
}
