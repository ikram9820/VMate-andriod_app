package com.example.vmate.Interfaces;

import java.io.IOException;

public interface VideoDownloader {

    String createDirectory() throws IOException;

    String getVideoId(String link);

    void DownloadVideo();
}
