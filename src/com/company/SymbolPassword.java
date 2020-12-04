package com.company;

public class SymbolPassword {

    public interface SymbolPasswordExitInterface {
        void isExit();
    }

    public static final char[] SERVICE = new char[]{'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};
    public static final char[] NUMBER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static final char[] APP_CASE_SYMBOL = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] LOW_CASE_SYMBOL = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private boolean use_SERVICE = false;
    private boolean use_NUMBER = false;
    private boolean use_APP_CASE_SYMBOL = false;
    private boolean use_LOW_CASE_SYMBOL = false;
    private SymbolPassword nextSymbol = null;
    private char symbol = ' ';
    private String posCharPass = "";
    private SymbolPasswordExitInterface exitInterface;

    public SymbolPassword(SymbolPasswordExitInterface exitInterface) {
        this.exitInterface = exitInterface;
    }

    public SymbolPassword(SymbolPasswordExitInterface exitInterface, String posCharPass) {
        this.exitInterface = exitInterface;
        setPosCharPass(posCharPass);
    }

    public void next() {
        int indexSymbol = posCharPass.indexOf(symbol);
        if (indexSymbol + 1 != posCharPass.length()) {
            symbol = posCharPass.charAt(indexSymbol + 1);
        } else {
            if (!idLastSymbol()) {
                nextSymbol.next();
                symbol = posCharPass.charAt(0);
            } else {
                exitInterface.isExit();
            }
        }
    }

    private void addSymbol() {
        if (idLastSymbol()) {
            this.nextSymbol = new SymbolPassword(exitInterface, posCharPass);
        } else
            this.nextSymbol.addSymbol();
    }

    private boolean idLastSymbol() {
        return nextSymbol == null;
    }

    public void setLength(int length) {
        for (int i = 0; i < length - 1; i++) {
            addSymbol();
        }
    }

    public void setPosCharPass(String posCharPass) {
        this.posCharPass = posCharPass;
        if (posCharPass.trim().length() != 0) {
            symbol = posCharPass.charAt(0);
            if (!idLastSymbol())
                this.nextSymbol.setPosCharPass(posCharPass);
        }
    }

    private void setupPosCharPass() {
        String allSymbolPass = "";
        if (use_SERVICE) {
            allSymbolPass += new String(SERVICE);
        }
        if (use_NUMBER) {
            allSymbolPass += new String(NUMBER);
        }
        if (use_APP_CASE_SYMBOL) {
            allSymbolPass += new String(APP_CASE_SYMBOL);
        }
        if (use_LOW_CASE_SYMBOL) {
            allSymbolPass += new String(LOW_CASE_SYMBOL);
        }
        setPosCharPass(allSymbolPass);
    }

    public void setUse_SERVICE(boolean use_SERVICE) {
        this.use_SERVICE = use_SERVICE;
        setupPosCharPass();
    }

    public void setUse_NUMBER(boolean use_NUMBER) {
        this.use_NUMBER = use_NUMBER;
        setupPosCharPass();
    }

    public void setUse_APP_CASE_SYMBOL(boolean use_APP_CASE_SYMBOL) {
        this.use_APP_CASE_SYMBOL = use_APP_CASE_SYMBOL;
        setupPosCharPass();
    }

    public void setUse_LOW_CASE_SYMBOL(boolean use_LOW_CASE_SYMBOL) {
        this.use_LOW_CASE_SYMBOL = use_LOW_CASE_SYMBOL;
        setupPosCharPass();
    }

    @Override
    public String toString() {
        if (idLastSymbol())
            return String.valueOf(symbol);
        else
            return nextSymbol.toString() + symbol;
    }
}