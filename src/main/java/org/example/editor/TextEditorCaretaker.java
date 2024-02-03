package org.example.editor;

import org.example.exception.InvalidOperationException;

import java.util.Stack;

public class TextEditorCaretaker {

  private final Stack<TextEditorMemento> undoHistory;
  private final Stack<TextEditorMemento> redoHistory;

  public TextEditorCaretaker() {
    this.undoHistory = new Stack<>();
    this.redoHistory = new Stack<>();
  }

  public void appendUndoHistory(TextEditorMemento textEditorMemento) {
    this.undoHistory.add(textEditorMemento);
  }

  public void appendRedoHistory(TextEditorMemento textEditorMemento) {
    this.redoHistory.add(textEditorMemento);
  }

  public TextEditorMemento undo() throws InvalidOperationException {
    if (undoHistory.isEmpty()) {
      throw new InvalidOperationException("No undo history is found!");
    }
    TextEditorMemento textEditorMemento = undoHistory.pop();
    redoHistory.add(textEditorMemento);
    return textEditorMemento;
  }

  public TextEditorMemento redo() throws InvalidOperationException {
    if (redoHistory.isEmpty()) {
      throw new InvalidOperationException("No redo history is found!");
    }
    TextEditorMemento textEditorMemento = redoHistory.pop();
    undoHistory.add(textEditorMemento);
    return textEditorMemento;
  }

  public void clearRedoHistory() {
    this.redoHistory.clear();
  }

}
