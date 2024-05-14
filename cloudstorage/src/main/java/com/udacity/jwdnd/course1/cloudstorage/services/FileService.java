package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.FileUpload;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<FileUpload> getFileListByUserId(Integer userId) {
        return fileMapper.getFileListByUserId(userId);
    }

    public FileUpload getFileByFileId(Integer fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public FileUpload getFileByUserIdAndFileName(Integer userId, String fileName) {
        return fileMapper.getFileByUserIdAndFileName(userId, fileName);
    }

    public int uploadFile(FileUpload file) {
        return fileMapper.uploadFile(file);
    }

    public int deleteFileByFileId(Integer fileId) {
        return fileMapper.deleteFileByFileId(fileId);
    }

}
