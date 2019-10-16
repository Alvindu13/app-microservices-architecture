package com.rattrapage.microservicebackup.microservicebackup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import java.io.*;
import java.util.Date;

@EnableBinding(Sink.class)
@SpringBootApplication
public class MicroserviceBackupApplication {


	public static void main(String[] args) {
		SpringApplication.run(MicroserviceBackupApplication.class, args);
	}


	@StreamListener(Sink.INPUT)
	public void handleMessage(Message message, FileApp file) throws IOException {
		System.out.println("Received Message is: " + message);
		System.out.println("Received File is: " + file.toString());

		if(file.contentLength > 0) {

			String typeFile = file.getMimeType().substring(file.getMimeType().indexOf("/") + 1);
			String fileName = "";
			if(typeFile.equals("markdown")){
				fileName = "storage/" + file.getName() + ".md";
				typeFile = ".md";
			}

			if(typeFile.equals("pdf")){
				fileName = "storage/" + file.getName() + ".pdf";
				typeFile = ".pdf";
			}
			File fileTest = new File(fileName);
			BufferedReader br;
			String str = "";


			try {
				br = new BufferedReader(new FileReader(fileTest));
				StringBuilder sb = new StringBuilder();
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
				br.close();
				String str1 = sb.toString();
				createBackup("backup_files/" + file.getName() + " - backup" + typeFile, str1);
				//System.out.println(str1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public static void createBackup(String filename, String... content)
			throws IOException{
		File originalFile = new File(filename);
		File backupFile = new File(originalFile.getCanonicalPath());

		originalFile.renameTo(backupFile);

		PrintWriter output = new PrintWriter(originalFile);
		for(String fileData : content){
			output.println(fileData);
		}
		output.close();
	}

	public static class FileApp {
		private String name;
		private String summary;
		private Date createdDate;
		@ContentId
		private String contentId;
		@ContentLength
		private long contentLength;
		private String mimeType = "text/plain";
		private String path;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public String getContentId() {
			return contentId;
		}

		public void setContentId(String contentId) {
			this.contentId = contentId;
		}

		public long getContentLength() {
			return contentLength;
		}

		public void setContentLength(long contentLength) {
			this.contentLength = contentLength;
		}

		public String getMimeType() {
			return mimeType;
		}

		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		@Override
		public String toString() {
			return "FileApp{" +
					"name='" + name + '\'' +
					", summary='" + summary + '\'' +
					", createdDate=" + createdDate +
					", contentId='" + contentId + '\'' +
					", contentLength=" + contentLength +
					", mimeType='" + mimeType + '\'' +
					", path='" + path + '\'' +
					'}';
		}
	}



	public static class Message{
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "Message{" +
					"message='" + message + '\'' +
					'}';
		}
	}
}
