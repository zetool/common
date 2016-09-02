package org.zetool.common.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SelectedElementsTest {
    
    /**
     * Spy class that allows checking the selection status.
     */
    private static class MockSelectable implements Selectable {
        Optional<Boolean> selectionStatus = Optional.empty();
        
        @Override
        public void setSelected(boolean value) {
            selectionStatus = Optional.of(value);
        }   
    }
    
    @Test
    public void objectInitialization() {
        SelectedElements<MockSelectable> selectedElements = new SelectedElements<>();
        
        assertThat(selectedElements.getSelectedList(), is(empty()));
        assertThat(selectedElements.getSelected(), is(nullValue()));
    }
    
    @Test
    public void add() {
        SelectedElements<MockSelectable> selectedElements = new SelectedElements<>();

        MockSelectable s1 = new MockSelectable();
        MockSelectable s2 = new MockSelectable();
        
        selectedElements.add(s1);
        selectedElements.add(s2);
        
        assertThat(selectedElements.getSelectedList(), contains(s1, s2));
        assertThat(selectedElements.getSelected(), is(equalTo(s1)));
        
        assertThat(s1.selectionStatus.get(), is(equalTo(true)));
        assertThat(s2.selectionStatus.get(), is(equalTo(true)));
    }
    
    @Test
    public void selectClearsOldContent() {
        SelectedElements<MockSelectable> selectedElements = new SelectedElements<>();

        MockSelectable s1 = new MockSelectable();
        MockSelectable s2 = new MockSelectable();
        MockSelectable s3 = new MockSelectable();
        
        selectedElements.add(s1);
        selectedElements.add(s2);
        selectedElements.select(s3);
        
        assertThat(selectedElements.getSelectedList(), contains(s3));
        assertThat(selectedElements.getSelected(), is(equalTo(s3)));
        
        assertThat(s1.selectionStatus.get(), is(equalTo(false)));
        assertThat(s2.selectionStatus.get(), is(equalTo(false)));
        assertThat(s3.selectionStatus.get(), is(equalTo(true)));
    }
    
    @Test
    public void addList() {
        SelectedElements<MockSelectable> selectedElements = new SelectedElements<>();

        MockSelectable s1 = new MockSelectable();
        MockSelectable s2 = new MockSelectable();
        
        selectedElements.add(Arrays.asList(s1, s2));
        
        assertThat(selectedElements.getSelectedList(), contains(s1, s2));
        assertThat(selectedElements.getSelected(), is(equalTo(s1)));
        
        assertThat(s1.selectionStatus.get(), is(equalTo(true)));
        assertThat(s2.selectionStatus.get(), is(equalTo(true)));
    }
    
    
    @Test
    public void clearList() {
        SelectedElements<MockSelectable> selectedElements = new SelectedElements<>();

        MockSelectable s1 = new MockSelectable();
        MockSelectable s2 = new MockSelectable();
        
        selectedElements.add(Arrays.asList(s1, s2));
        selectedElements.clear();
        
        assertThat(selectedElements.getSelectedList(), is(empty()));
        assertThat(selectedElements.getSelected(), is(nullValue()));
        
        assertThat(s1.selectionStatus.get(), is(equalTo(false)));
        assertThat(s2.selectionStatus.get(), is(equalTo(false)));
    }
}
