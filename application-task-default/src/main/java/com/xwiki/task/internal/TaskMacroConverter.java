/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.xwiki.task.internal;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.confluence.filter.internal.macros.AbstractMacroConverter;

import com.xwiki.task.model.Task;

/**
 * Convert task macros.
 *
 * @version $Id$
 * @since 3.0
 */
@Component
@Singleton
@Named("task")
public class TaskMacroConverter extends AbstractMacroConverter
{
    private static final String TASK_STATUS_PARAMETER = "status";

    private static final String TASK_ID_PARAMETER = "id";

    private static final String TASK_REFERENCE_PARAMETER = "reference";

    private static final String TASK_REFERENCE_PREFIX = "Task_";

    @Override
    protected Map<String, String> toXWikiParameters(String confluenceId, Map<String, String> confluenceParameters,
        String content)
    {
        Map<String, String> params = new HashMap<>();
        // TODO: Use a configurable value instead of "Done".
        String xwikiStatus =
            !confluenceParameters.get(TASK_STATUS_PARAMETER).equals("complete")
                && !confluenceParameters.get(TASK_STATUS_PARAMETER).equals(Task.STATUS_DONE) ? Task.STATUS_IN_PROGRESS
                : Task.STATUS_DONE;
        String xwikiIdParam = confluenceParameters.get(TASK_ID_PARAMETER) != null
            ? TASK_REFERENCE_PREFIX + confluenceParameters.get(TASK_ID_PARAMETER)
            : confluenceParameters.get(TASK_REFERENCE_PARAMETER);

        params.put(TASK_STATUS_PARAMETER, xwikiStatus);
        params.put(TASK_REFERENCE_PARAMETER, xwikiIdParam);
        return params;
    }
}
