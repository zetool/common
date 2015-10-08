/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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
package org.zetool.common.algorithm;

import java.util.concurrent.Callable;

/**
 *
 * @param <P> the problem type that is solved by the algorithm
 * @param <S> the solution type that is generated by the algorithm
 * @author Jan-Philipp Kappmeier
 */
public interface Algorithm<P, S> extends Runnable, Callable<S> {

  /**
   * Returns the instance of the problem that is to be solved.
   *
   * @return the instance of the problem that is to be solved.
   */
  public P getProblem();

  /**
   * Specifies the instance of the problem this algorithm is going to solve.
   *
   * @param problem the instance of the problem that is to be solved.
   * @throws IllegalStateException if the algorithm is running
   */
  public void setProblem( P problem );

  /**
   * Returns the solution computed by the algorithm.
   *
   * @return the solution to the algorithm.
   * @throws IllegalStateException if the problem has not been solved yet.
   */
  public S getSolution();

  /**
   * Returns whether the algorithm is currently begin executed.
   *
   * @return {@code true} if this algorithm is currently running and {@code false} otherwise.
   */
  public boolean isRunning();

  /**
   * The framework method for executing the algorithm. It is responsible for recording the runtime of the actual
   * algorithm in addition to handling exceptions and recording the solution to the problem instance.
   * 
   * Calling the method solves the problem, afterwords it can be accessed using {@link #getSolution() }.
   *
   * @throws IllegalStateException if the instance of the problem has not been specified yet.
   */
  @Override
  public void run();

  /**
   * A framework method for executing the algorithm and returning the result.
   * 
   * Calling the method solves the problem and returns the solution. The solution is stored and can be accessed
   * again using {@link #getSolution() }.
   *
   * @return the solution to the algorithm.
   */
  @Override
  public S call();
}
