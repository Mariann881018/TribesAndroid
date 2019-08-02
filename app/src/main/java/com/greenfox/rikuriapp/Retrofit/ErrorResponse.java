package com.greenfox.rikuriapp.Retrofit;

import java.sql.Timestamp;
import java.time.LocalDateTime;

    public class ErrorResponse {

        private String httpStatus;
        private String message;
        private String path;
        private Timestamp timeStamp;

        public ErrorResponse() {
        }

        public String getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(String httpStatus) {
            this.httpStatus = httpStatus;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Timestamp getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(Timestamp timeStamp) {
            this.timeStamp = timeStamp;
        }
}
