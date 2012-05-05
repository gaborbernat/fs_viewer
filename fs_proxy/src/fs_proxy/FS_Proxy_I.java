/*
 * FS_Proxy_I.java ->
 * Copyright (C) 2012-05-06 Gábor Bernát
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

package fs_proxy;

/**
 * Created with IntelliJ IDEA.
 * User: gabor.bernat
 * Date: 5/5/12
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FS_Proxy_I {
    public int getRefreshInterval();

    public void setRefreshInterval(int refreshInterval);

    public void run();

    public void run(int refreshInterval);
}
