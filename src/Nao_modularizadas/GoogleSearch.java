package Nao_modularizadas;

import java.awt.*;
import java.net.URI;
import java.net.MalformedURLException;
import java.net.URL;

import org.sikuli.api.*;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

import static org.sikuli.api.API.*;

public class GoogleSearch {
    private void searchOnGoogle(String find) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                String new_find = find.replace(" ", "+");
                desktop.browse(new URI("https://www.google.com/search?q=" + new_find));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}