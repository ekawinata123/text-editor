package org.example.editor;

import org.example.enumeration.TextEditorCommand;
import org.example.exception.IllegalParameterException;
import org.example.exception.InvalidOperationException;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TextEditor {

  private int cursorIndex;
  private final List<Character> characters;
  private final TextEditorCaretaker textEditorCaretaker;

  public TextEditor() {
    this.cursorIndex = 0;
    this.characters = new LinkedList<>();
    this.textEditorCaretaker = new TextEditorCaretaker();
  }

  public void type(String input) throws IllegalParameterException {
    if (Objects.isNull(input)) {
      throw new IllegalParameterException("text");
    }
    ListIterator<Character> iterator = characters.listIterator(this.cursorIndex);
    for (char c : input.toCharArray()) {
      iterator.add(c);
      cursorIndex++;
    }
    textEditorCaretaker.appendUndoHistory(new TextEditorMemento(input, TextEditorCommand.TYPE, this.cursorIndex));
    textEditorCaretaker.clearRedoHistory();
  }

  public void delete() {
    ListIterator<Character> iterator = characters.listIterator(this.cursorIndex);

    if (iterator.hasPrevious()) {
      char removedChar = iterator.previous();
      iterator.remove();
      this.cursorIndex = Math.max(0, this.cursorIndex - 1);
      textEditorCaretaker.appendUndoHistory(new TextEditorMemento(String.valueOf(removedChar), TextEditorCommand.DELETE, this.cursorIndex));
      textEditorCaretaker.clearRedoHistory();
    }
  }

  public void moveLeft() {
    this.cursorIndex = Math.max(0, this.cursorIndex - 1);
  }

  public void moveRight() {
    if (this.cursorIndex == this.characters.size()) {
      return;
    }
    this.cursorIndex += 1;
  }

  public String getCurrentText() {
    return characters.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(""));
  }

  public void undo() {
    try {
      TextEditorMemento textEditorMemento = textEditorCaretaker.undo();
      if (TextEditorCommand.TYPE == textEditorMemento.command()) {
        handleTypeUndo(textEditorMemento.input(), textEditorMemento.cursorIndex());
      } else {
        handleDeleteUndo(textEditorMemento.input(), textEditorMemento.cursorIndex());
      }
      textEditorCaretaker.appendRedoHistory(textEditorMemento);
    } catch (InvalidOperationException e) {
      System.out.println(e.getMessage());
    }
  }

  private void handleTypeUndo(String input, int cursorIndex) {
    ListIterator<Character> listIterator = characters.listIterator(cursorIndex);
    IntStream.range(0, input.length())
            .forEach(ignore -> {
              listIterator.previous();
              listIterator.remove();
            });
    this.cursorIndex = this.characters.isEmpty() ? 0 : this.cursorIndex - input.length() - 1;
  }

  private void handleDeleteUndo(String input, int cursorIndex) {
    ListIterator<Character> listIterator = characters.listIterator(cursorIndex);
    IntStream.range(0, input.length())
            .forEach(index -> listIterator.add(input.charAt(index)));
    this.cursorIndex += (input.length() - 1);
  }

}
