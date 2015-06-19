package com.fxb.razor.utils.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.common.*;

public class HelpBoard
{
    public ArrowRect arrowRect;
    public Group groupBoard;
    public MyImage imgHand;
    public MyImage imgShade;
    public int insStep;
    public Actor tempTarget;
    
    public HelpBoard() {
        this.insStep = 0;
    }
    
    public static HelpBoard createDialogBoard(final Stage stage, final Actor actor, final String s, final int n, final int n2) {
        final HelpBoard helpBoard = new HelpBoard();
        if (stage != null && actor.getParent() != stage.getRoot()) {
            final Group parent = actor.getParent();
            parent.toFront();
            helpBoard.imgShade = MyShade.createShade(parent);
            actor.toFront();
            helpBoard.arrowRect = ArrowRect.createArrowRect(parent, actor);
            helpBoard.imgHand = UiHandle.createHand(parent, actor);
            helpBoard.groupBoard = UiHandle.createInsturctionBoard(parent, s, actor, n, n2);
        }
        return helpBoard;
    }
    
    public static HelpBoard createHelpBoard(final Stage stage, final Actor actor, final String s, final int n, final int n2) {
        final HelpBoard helpBoard = new HelpBoard();
        helpBoard.imgShade = MyShade.createShade(stage);
        actor.toFront();
        helpBoard.arrowRect = ArrowRect.createArrowRect(stage, actor);
        helpBoard.imgHand = UiHandle.createHand(stage, actor);
        helpBoard.groupBoard = UiHandle.createInsturctionBoard(stage, s, actor, n, n2);
        return helpBoard;
    }
    
    public static HelpBoard createPanHelp(final Stage stage, final Actor actor, final String s, final int n, final int n2, final MyScrollPane myScrollPane) {
        final HelpBoard helpBoard = new HelpBoard();
        helpBoard.imgShade = MyShade.createShade(stage);
        myScrollPane.toFront();
        actor.toFront();
        helpBoard.arrowRect = ArrowRect.createArrowRect(stage, actor);
        helpBoard.imgHand = UiHandle.createHand(stage, actor);
        helpBoard.groupBoard = UiHandle.createInsturctionBoard(stage, s, actor, n, n2);
        return helpBoard;
    }
    
    public static int getInsStep(final HelpBoard helpBoard) {
        if (helpBoard == null) {
            return -1;
        }
        return helpBoard.insStep;
    }
    
    public void remove() {
        if (this.imgShade != null) {
            this.imgShade.remove();
        }
        if (this.imgHand != null) {
            this.imgHand.remove();
        }
        if (this.arrowRect != null) {
            this.arrowRect.remove();
        }
        if (this.groupBoard != null) {
            this.groupBoard.remove();
        }
        if (this.tempTarget != null) {
            this.tempTarget.remove();
        }
    }
}
