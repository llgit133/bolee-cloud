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
package com.itheima.bolee.framework.rule.model.rule;
/**
 * @author Jacky.gao
 * @since 2015年6月14日
 */
public class ParenValue extends AbstractValue {
	private Value value;
	@Override
	public String getId() {
		String id="(";
		if(value!=null){
			id+=value.getId();
		}
		id+=")";
		return id;
	}
	@Override
	public ValueType getValueType() {
		return ValueType.Paren;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
}
