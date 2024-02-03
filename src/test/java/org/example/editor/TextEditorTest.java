package org.example.editor;

import org.example.exception.IllegalParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextEditorTest {

  private TextEditor textEditor;

  @BeforeEach
  public void initialize() {
    this.textEditor = new TextEditor();
  }

  @Test
  void type_shouldAppendStringAsChars_whenInputIsNonEmpty() throws IllegalParameterException {
    String expectedText = "abc\ndef";

    this.textEditor.type("abc");
    this.textEditor.type("\ndef");
    String result = textEditor.getCurrentText();

    Assertions.assertEquals(result, expectedText);
  }

  @Test
  void type_shouldThrowInvalidParameterException_whenInputIsNull() {
    Assertions.assertThrows(IllegalParameterException.class, () -> textEditor.type(null));
  }


  @Test
  void delete_shouldRemoveCurrentCharAtCursor_whenCursorIsNotZero() throws IllegalParameterException {
    String initialText = "abcd";
    String expectedText = "ab";
    this.textEditor.type(initialText);

    this.textEditor.delete();
    this.textEditor.delete();
    String result = this.textEditor.getCurrentText();

    Assertions.assertEquals(result, expectedText);
  }

  @Test
  void moveLeft_shouldMoveCursorLeftOneUnit_whenCurrentIndexIsNotZero() throws IllegalParameterException {
    String initialText = "abcd";
    this.textEditor.type(initialText);

    this.textEditor.moveLeft();
    this.textEditor.moveLeft();
    this.textEditor.type("zz");
    String result = this.textEditor.getCurrentText();

    Assertions.assertEquals(result, "abzzcd");
  }

  @Test
  void moveLeft_shouldKeepCursorPosition_whenCurrentIndexIsZero() throws IllegalParameterException {
    this.textEditor.moveLeft();
    this.textEditor.type("a");
    String result = this.textEditor.getCurrentText();

    Assertions.assertEquals(result, "a");
  }

  @Test
  void moveRight_shouldMoveCursorRightOneUnit_whenCurrentIndexIsNotEqualsTextLength() throws IllegalParameterException {
    String initialText = "abcd";
    this.textEditor.type(initialText);
    this.textEditor.moveLeft();
    this.textEditor.moveLeft();

    this.textEditor.moveRight();
    this.textEditor.type("zz");
    String result = this.textEditor.getCurrentText();

    Assertions.assertEquals(result, "abczzd");
  }

  @Test
  void moveRight_shouldNotMoveCursor_whenCurrentIndexIsEqualsToTextLengthSize() throws IllegalParameterException {
    this.textEditor.type("zzz");
    this.textEditor.moveRight();
    this.textEditor.moveRight();
    this.textEditor.moveRight();

    this.textEditor.type("ab");
    String result = this.textEditor.getCurrentText();

    Assertions.assertEquals(result, "zzzab");
  }

  @Test
  void undo_shouldDeleteLastTypedInput() throws IllegalParameterException {
    String expectedText = "ww";
    this.textEditor.type("a");
    this.textEditor.type("123");
    this.textEditor.moveLeft();

    this.textEditor.undo();
    this.textEditor.undo();
    this.textEditor.type("www");
    this.textEditor.delete();
    String result = this.textEditor.getCurrentText();

    Assertions.assertEquals(expectedText, result);
  }

}
