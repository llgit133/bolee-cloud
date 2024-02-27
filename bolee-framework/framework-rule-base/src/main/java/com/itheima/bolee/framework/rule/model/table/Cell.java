/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.itheima.bolee.framework.rule.model.table;

import com.itheima.bolee.framework.rule.action.Action;
import com.itheima.bolee.framework.rule.model.rule.Value;

/**
 * @author Jacky.gao
 * @since 2015年1月19日
 */
public class Cell {
	private int row;
	private int col;
	private int rowspan;
	private Joint joint;
	private Value value;
	private Action action;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	public Joint getJoint() {
		return joint;
	}
	public void setJoint(Joint joint) {
		this.joint = joint;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
}
