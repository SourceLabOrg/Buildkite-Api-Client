/**
 * Copyright 2023 SourceLab.org https://github.com/SourceLabOrg/Buildkite-Api-Client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.sourcelab.buildkite.api.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Artifact {
    private final String id;
    private final String jobId;
    private final String url;
    private final String downloadUrl;
    private final String state;
    private final String path;
    private final String dirname;
    private final String filename;
    private final String mimeType;
    private final long fileSize;
    private final String globPath;
    private final String originalPath;
    private final String sha1sum;

    /**
     * Constructor.
     */
    @JsonCreator
    public Artifact(
        @JsonProperty("id") final String id,
        @JsonProperty("job_id") final String jobId,
        @JsonProperty("url") final String url,
        @JsonProperty("download_url") final String downloadUrl,
        @JsonProperty("state") final String state,
        @JsonProperty("path") final String path,
        @JsonProperty("dirname") final String dirname,
        @JsonProperty("filename") final String filename,
        @JsonProperty("mime_type") final String mimeType,
        @JsonProperty("file_size") final Long fileSize,
        @JsonProperty("glob_path") final String globPath,
        @JsonProperty("original_path") final String originalPath,
        @JsonProperty("sha1sum") final String sha1sum
    ) {
        this.id = id;
        this.jobId = jobId;
        this.url = url;
        this.downloadUrl = downloadUrl;
        this.state = state;
        this.path = path;
        this.dirname = dirname;
        this.filename = filename;
        this.mimeType = mimeType;
        this.fileSize = fileSize == null ? 0 : fileSize;
        this.globPath = globPath;
        this.originalPath = originalPath;
        this.sha1sum = sha1sum;
    }

    public String getId() {
        return id;
    }

    public String getJobId() {
        return jobId;
    }

    public String getUrl() {
        return url;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getState() {
        return state;
    }

    public String getPath() {
        return path;
    }

    public String getDirname() {
        return dirname;
    }

    public String getFilename() {
        return filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getGlobPath() {
        return globPath;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public String getSha1sum() {
        return sha1sum;
    }

    @Override
    public String toString() {
        return "Artifact{"
            + "id='" + id + '\''
            + ", jobId='" + jobId + '\''
            + ", url='" + url + '\''
            + ", downloadUrl='" + downloadUrl + '\''
            + ", state='" + state + '\''
            + ", path='" + path + '\''
            + ", dirname='" + dirname + '\''
            + ", filename='" + filename + '\''
            + ", mimeType='" + mimeType + '\''
            + ", fileSize=" + fileSize
            + ", globPath='" + globPath + '\''
            + ", originalPath='" + originalPath + '\''
            + ", sha1sum='" + sha1sum + '\''
            + '}';
    }
}
