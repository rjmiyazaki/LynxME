package br.com.lynx.util;

import android.content.DialogInterface;

public class CommandWrapper implements DialogInterface.OnClickListener {
  private Command command;
  
  public CommandWrapper(Command command) {
    this.command = command;
  }

  public void onClick(DialogInterface dialog, int which) {
    dialog.dismiss();
    command.execute();
  }
}
