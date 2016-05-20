/**
 * Copyright (c) 2012 totemo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.minebench.zombe.core.utils;

import java.util.concurrent.ConcurrentLinkedQueue;

// ----------------------------------------------------------------------------

/**
 * A queue of tasks that must be run synchronously to the main thread.
 * 
 * This minimises locking and allows various structures such as BlockEditSet to
 * be modified at an appropriate time, when not being traversed, thus avoiding a
 * ConcurrentModificationException.
 */
public class SyncTaskQueue
{
  // --------------------------------------------------------------------------
  /**
   * Single instance.
   */
  public static SyncTaskQueue instance = new SyncTaskQueue();

  // --------------------------------------------------------------------------
  /**
   * Add a task to the queue.
   * 
   * @param task the task.
   */
  public void addTask(Runnable task)
  {
    _taskQueue.add(task);
  }

  // --------------------------------------------------------------------------
  /**
   * Run and dequeue all tasks.
   */
  public void runTasks()
  {
    for (;;)
    {
      Runnable task = _taskQueue.poll();
      if (task == null)
      {
        break;
      }
      task.run();
    }
  }

  // --------------------------------------------------------------------------
  /**
   * Queue of tasks to execute in the order that they should run.
   */
  protected ConcurrentLinkedQueue<Runnable> _taskQueue = new ConcurrentLinkedQueue<Runnable>();
} // class SyncTaskQueue