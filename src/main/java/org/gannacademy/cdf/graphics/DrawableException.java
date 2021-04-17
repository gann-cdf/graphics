package org.gannacademy.cdf.graphics;

/**
 * Superclass of exceptions thrown by members of {@code org.gannacademy.cdf.graphics} package
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues" target="_blank">Seth Battis</a>
 */
public class DrawableException extends Exception {
  /**
   * Construct a new DrawableException
   * @param message explaining exception
   */
  public DrawableException(String message) {
    super(message);
  }
}
