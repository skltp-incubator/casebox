/*
 * Copyright 2010 Inera
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *
 *   Boston, MA 02111-1307  USA
 */

package se.inera.ifv.casebox.core.service.impl;

import se.inera.ifv.casebox.core.entity.MessageStatus;
import se.inera.ifv.casebox.core.service.CareUnitInfo;

/**
 * @author Pär Wenåker
 *
 */
public class CareUnitInfoQuestionInternal extends CareUnitInfoInternal {

    /**
     * @param name
     * @param status
     * @param count
     */
    public CareUnitInfoQuestionInternal(String name, MessageStatus status, long count) {
        super(name, "Question", status, count);
    }

    /* (non-Javadoc)
     * @see se.inera.ifv.casebox.core.service.impl.CareUnitInfoInternal#handle(se.skl.ifv.casebox.core.service.CareUnitInfo)
     */
    @Override
    public void update(CareUnitInfo info) {
        switch (this.getStatus()) {
        case RECEIVED:
            info.addQuestionsInArrived(this.getCount());
            break;
        case RETRIEVED:
            info.addQuestionsInRetreived(this.getCount());
            break;
        }
    }

}
