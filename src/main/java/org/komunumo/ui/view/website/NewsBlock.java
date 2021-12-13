/*
 * Komunumo - Open Source Community Manager
 * Copyright (C) Marcus Fihlon and the individual contributors to Komunumo.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.komunumo.ui.view.website;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.jetbrains.annotations.NotNull;
import org.komunumo.data.service.NewsService;
import org.komunumo.ui.component.More;

@CssImport("./themes/komunumo/views/website/news-block.css")
public class NewsBlock extends ContentBlock {

    public NewsBlock(@NotNull final NewsService newsService) {
        super("News");
        addClassName("news-block");
        setContent(new HorizontalLayout(createNewsContent(newsService), createNewsletterForm()));
    }

    private Component createNewsContent(@NotNull final NewsService newsService) {
        final var container = new Div();

        final var news = newsService.getLatestNews();
        if (news != null) {
            container.add(new H2(news.title()));
            if (!news.subtitle().isBlank()) {
                container.add(new H3(news.subtitle()));
            }
            container.add(new Html("<div>%s</div>".formatted(news.description())));
            container.add(new More("javascript:alert('The news feature is not completely implemented!');"));
        }

        return container;
    }

    private Component createNewsletterForm() {
        return new Div(
                new H2("Stay informed about events"),
                new Paragraph("Please register here with your e-mail to receive announcements for upcoming JUG Switzerland events.")
        );
    }

}
