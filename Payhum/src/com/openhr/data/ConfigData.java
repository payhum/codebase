package com.openhr.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "payhum_config", catalog = "payhumrepo", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigData.findAll", query = "SELECT b FROM ConfigData b"),
    @NamedQuery(name = "ConfigData.findByName", query = "SELECT b FROM ConfigData b WHERE b.configName = ?")})
public class ConfigData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "configName", nullable = false, length = 45)
    private String configName;
    
    @Basic(optional = false)
    @Column(name = "configValue", nullable = false, length = 45)
    private String configValue;

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
}
