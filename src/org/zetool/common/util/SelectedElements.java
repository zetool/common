/* zet evacuation tool copyright (c) 2007-16 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * A collection of several elements that can be 'selected'. If the selection changes, i.e. a new element is added or
 * the list is cleared, the {@link java.util.Observer}s are notified.
 * 
 * @param <E> the type of objects that can be selected
 * @author Jan-Philipp Kappmeier
 */
public class SelectedElements<E extends Selectable> extends Observable {

    private final List<E> selected = new LinkedList<>();

    /**
     * Clears the selection. All elements are removed and their selection status is set to unselected.
     */
    public void clear() {
        if (selected.isEmpty()) {
            return;
        }
        clearInternal();
        super.setChanged();
        super.notifyObservers();
    }

    private void clearInternal() {
        for (E p : selected) {
            p.setSelected(false);
        }
        selected.clear();
    }

    /**
     * Selects a single element. If any other elements are contained in the {@code SelectedElements}, their selection
     * status is resetted to unselected.
     * 
     * @param toSelect the element that is to be selected
     */
    public void select(E toSelect) {
        clearInternal();
        addInternal(toSelect);
        super.setChanged();
        super.notifyObservers(toSelect);
    }

    /**
     * Returns an unmodifiable {@link List} of all elements in the current selection.
     * 
     * @return a {@link List} of the selected elements
     */
    public List<E> getSelectedList() {
        return Collections.unmodifiableList(selected);
    }

    /**
     * Returns the selected element (if it is only one) or the first of the list of selected elements. If no element is
     * selected, {@literal null} is returned. 
     * @see #getSelectedList() 
     * @return a single selected element
     */
    public E getSelected() {
        return selected.isEmpty() ? null : selected.get(0);
    }

    /**
     * Adds a single {@link Selectable} element to the list. Its status is set to be selected.
     * 
     * @param element the element to be added
     */
    public void add(E element) {
        addInternal(element);
        super.setChanged();
        super.notifyObservers();
    }

    /**
     * Adds multiple {@link Selectable} elements to the list. The status of each element is set to be selected.
     * 
     * @param elements the list of elements
     */
    public void add(Collection<E> elements) {
        for (E element : elements) {
            addInternal(element);
        }
        super.setChanged();
        super.notifyObservers();
    }

    private void addInternal(E element) {
        element.setSelected(true);
        selected.add(element);
    }
}
