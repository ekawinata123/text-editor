package org.example.editor;

import org.example.exception.IllegalParameterException;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Collectors;

public class TextEditor {

  private int cursorIndex;
  private final List<Character> characters;

  public TextEditor() {
    this.cursorIndex = 0;
    this.characters = new LinkedList<>();
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
  }

  public void delete() {
    ListIterator<Character> iterator = characters.listIterator(this.cursorIndex);

    if (iterator.hasPrevious()) {
      iterator.previous();
      iterator.remove();
      this.cursorIndex = Math.max(0, this.cursorIndex - 1);
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

}
