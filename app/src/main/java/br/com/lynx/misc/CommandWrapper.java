package br.com.lynx.misc;

import android.content.DialogInterface;

/**
 * Created by rogerio on 18/02/2016.
 */
public class CommandWrapper implements DialogInterface.OnClickListener{
    private Command command;

    public CommandWrapper(Command command){
        this.command = command;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        command.execute();
    }
}
