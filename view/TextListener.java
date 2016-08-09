package view;

import fsm.Environment;

public interface TextListener extends Environment<String>
{
    /**
     * Empties any current input
     */
    public void clearText();
}
