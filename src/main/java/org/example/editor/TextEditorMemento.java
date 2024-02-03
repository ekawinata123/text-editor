package org.example.editor;

import org.example.enumeration.TextEditorCommand;

public record TextEditorMemento(String input, TextEditorCommand command, int cursorIndex) {

}
