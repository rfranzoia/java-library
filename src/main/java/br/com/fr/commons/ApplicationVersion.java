/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.io.Serializable;

/**
 *
 * @author Romeu Franzoia Jr
 */
public class ApplicationVersion implements Serializable {

    private static final long serialVersionUID = 3332335942579136681L;
    
    private long major;
    private long minor;
    private long build;

    public ApplicationVersion() {
    }
    
    public ApplicationVersion(long major, long minor, long build) {
        this.major = major;
        this.minor = minor;
        this.build = build;
    }

    public long getMajor() {
        return major;
    }

    public void setMajor(long major) {
        this.major = major;
    }

    public long getMinor() {
        return minor;
    }

    public void setMinor(long minor) {
        this.minor = minor;
    }

    public long getBuild() {
        return build;
    }

    public void setBuild(long build) {
        this.build = build;
    }

    @Override
    public String toString() {
        return "" + major + "." + minor + "." + build;
    }

    @Override
    public int hashCode() {
        final long prime = 31;
        long result = 1;
        result = prime * result + major;
        result = prime * result + minor;
        return (int) result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ApplicationVersion other = (ApplicationVersion) obj;

        if (major != other.getMajor() || minor != other.getMinor() || build != other.getBuild()) {
            return false;
        }

        return true;
    }

}
