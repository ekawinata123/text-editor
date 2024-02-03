package org.example.editor;

import org.example.enumeration.TextEditorCommand;
import org.example.exception.InvalidOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextEditorCaretakerTest {

  private TextEditorCaretaker textEditorCaretaker;

  @BeforeEach
  public void initialize() {
    this.textEditorCaretaker = new TextEditorCaretaker();
  }

  @Test
  void appendUndoHistory_shouldAddUndoHistory() throws InvalidOperationException {
    TextEditorMemento textEditorMemento = new TextEditorMemento("test1", TextEditorCommand.TYPE, 0);

    textEditorCaretaker.appendUndoHistory(textEditorMemento);
    TextEditorMemento result = textEditorCaretaker.undo();

    Assertions.assertEquals(result, textEditorMemento);
  }

  @Test
  void appendRedoHistory_shouldAddRedoHistory() throws InvalidOperationException {
    TextEditorMemento textEditorMemento = new TextEditorMemento("test1", TextEditorCommand.TYPE, 0);

    textEditorCaretaker.appendRedoHistory(textEditorMemento);
    TextEditorMemento result = textEditorCaretaker.redo();

    Assertions.assertEquals(result, textEditorMemento);
  }

  @Test
  void undo_shouldAddLatestHistoryToRedoHistory() throws InvalidOperationException {
    TextEditorMemento textEditorMemento = new TextEditorMemento("test1", TextEditorCommand.TYPE, 0);

    textEditorCaretaker.appendUndoHistory(textEditorMemento);
    TextEditorMemento undoResult = textEditorCaretaker.undo();
    TextEditorMemento redoResult = textEditorCaretaker.redo();

    Assertions.assertEquals(undoResult, textEditorMemento);
    Assertions.assertEquals(redoResult, textEditorMemento);
  }

  @Test
  void undo_shouldThrowException_whenUndoStackIsEmpty() {
    Assertions.assertThrows(InvalidOperationException.class, () -> textEditorCaretaker.undo());
  }

  @Test
  void redo_shouldThrowException_whenRedoStackIsEmpty() {
    Assertions.assertThrows(InvalidOperationException.class, () -> textEditorCaretaker.redo());
  }

  @Test
  void clearRedoHistory_shouldEmptyRedoStack() {
    TextEditorMemento textEditorMemento = new TextEditorMemento("abc", TextEditorCommand.TYPE, 0);
    textEditorCaretaker.appendRedoHistory(textEditorMemento);

    textEditorCaretaker.clearRedoHistory();

    Assertions.assertThrows(InvalidOperationException.class, () -> textEditorCaretaker.redo());
  }
}
