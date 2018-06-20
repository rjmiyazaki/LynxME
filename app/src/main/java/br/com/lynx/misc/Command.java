package br.com.lynx.misc;

/**
 * Created by rogerio on 18/02/2016.
 */
public interface Command {
    public void execute();

    public static final Command NO_OP = new Command() {
        public void execute() {
        }
    };
}
