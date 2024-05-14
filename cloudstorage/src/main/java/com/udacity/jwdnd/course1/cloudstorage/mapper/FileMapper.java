package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.FileUpload;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileUpload> getFileListByUserId(int userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    FileUpload getFileByFileId(int fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND filename LIKE #{fileName}")
    FileUpload getFileByUserIdAndFileName(int userId, String fileName);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int uploadFile(FileUpload file);


    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    public int deleteFileByFileId(int fileId);

}