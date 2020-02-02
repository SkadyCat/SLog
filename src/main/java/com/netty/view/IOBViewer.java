package com.netty.view;

public interface IOBViewer {

    void broadCast(ViewInfo info);
    void addViewer(IViewer viewer);
    void removeViewer(IViewer viewer);




}
