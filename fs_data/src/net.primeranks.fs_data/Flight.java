/*
 * Flight.java ->
 * Copyright (C) 2012-05-08 Gábor Bernát
 * Created at: [Budapest University of Technology and Economics - Deparment of Automation and Applied Informatics]
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package net.primeranks.fs_data;

import javax.persistence.Id;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "flight_meta")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "userId", "startDate", "endDate", "periodicity"})
public class Flight {
    public Flight() {
    }                // Required for te JAXB Magic

    @Id
    @XmlAttribute
    private Long id;

    private Long userId;
    private long startDate;
    private long endDate;
    private int periodicity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getStartDate() {
        return this.startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return this.endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getPeriodicity() {
        return this.periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}