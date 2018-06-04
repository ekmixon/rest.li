/*
   Copyright (c) 2012 LinkedIn Corp.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

/**
 * $Id: $
 */

package com.linkedin.restli.internal.server.methods.arguments;


import com.linkedin.data.DataMap;
import com.linkedin.data.template.DataTemplateUtil;
import com.linkedin.data.template.RecordTemplate;
import com.linkedin.restli.internal.server.RoutingResult;
import com.linkedin.restli.internal.server.util.ArgumentUtils;
import com.linkedin.restli.server.RestLiRequestData;
import com.linkedin.restli.server.RestLiRequestDataImpl;


/**
 * @author Josh Walker
 * @version $Revision: $
 */
public class UpdateArgumentBuilder implements RestLiArgumentBuilder
{
  @Override
  public Object[] buildArguments(RestLiRequestData requestData, RoutingResult routingResult)
  {
    final Object[] positionalArgs;
    if (requestData.hasKey())
    {
      positionalArgs = new Object[] { requestData.getKey(), requestData.getEntity() };
    }
    else
    {
      positionalArgs = new Object[] { requestData.getEntity() };
    }
    return ArgumentBuilder.buildArgs(positionalArgs,
                                     routingResult.getResourceMethod(),
                                     routingResult.getContext(),
                                     null);
  }

  @Override
  public RestLiRequestData extractRequestData(RoutingResult routingResult, DataMap dataMap)
  {
    RecordTemplate record = DataTemplateUtil.wrap(dataMap, ArgumentUtils.getValueClass(routingResult));
    RestLiRequestDataImpl.Builder builder = new RestLiRequestDataImpl.Builder().entity(record);
    if (ArgumentUtils.hasResourceKey(routingResult))
    {
      Object keyValue = ArgumentUtils.getResourceKey(routingResult);
      builder.key(keyValue);
    }
    return builder.build();
  }
}
