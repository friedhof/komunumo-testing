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

package org.komunumo.ui.view.website.sponsors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Article;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jetbrains.annotations.NotNull;
import org.komunumo.data.db.enums.PageParent;
import org.komunumo.data.db.enums.SponsorLevel;
import org.komunumo.data.entity.SponsorEntity;
import org.komunumo.data.service.DatabaseService;
import org.komunumo.ui.view.website.ContentBlock;
import org.komunumo.ui.view.website.SubMenu;
import org.komunumo.ui.view.website.SubMenuItem;
import org.komunumo.ui.view.website.WebsiteLayout;
import org.komunumo.util.URLUtil;

import java.io.Serial;

@Route(value = "sponsors", layout = WebsiteLayout.class)
@CssImport("./themes/komunumo/views/website/sponsors-view.css")
@AnonymousAllowed
public final class SponsorsView extends ContentBlock implements AfterNavigationObserver {

    @Serial
    private static final long serialVersionUID = 1434267042866729347L;
    private final DatabaseService databaseService;

    public SponsorsView(@NotNull final DatabaseService databaseService) {
        super("Supporting Members");
        this.databaseService = databaseService;
        addClassName("sponsors-view");
    }

    @Override
    public void afterNavigation(@NotNull final AfterNavigationEvent event) {
        final var url = event.getLocation().getPath();
        final var subMenu = new SubMenu();
        subMenu.add(new SubMenuItem("sponsors", "Our sponsors", url.equals("sponsors")));
        databaseService.getPages(PageParent.Sponsors).forEach(page ->
                subMenu.add(new SubMenuItem(page.getCompletePageUrl(), page.getTitle(), url.equals(page.getCompletePageUrl()))));
        setSubMenu(subMenu);
        final String pageTitle;
        if (url.equals("sponsors")) {
            showSponsors();
            pageTitle = "Supporting Members";
        } else {
            final var page = loadPage(databaseService, url);
            pageTitle = page.getTitle();
        }
        this.getUI().ifPresent(ui -> ui.getPage().setTitle("%s: %s".formatted(databaseService.configuration().getWebsiteName(), pageTitle)));
    }

    private void showSponsors() {
        setContent(
                new H2("%s Sponsors".formatted(databaseService.configuration().getWebsiteName())),
                new Paragraph("""
                        The JUG Switzerland is supported by industry sponsors. We appreciate their commitment to the
                        JUG Switzerland and we greatly value their active contribution to the JAVA software and
                        technology community."""),
                createSponsorSection(SponsorLevel.Platinum),
                createSponsorSection(SponsorLevel.Gold),
                createSponsorSection(SponsorLevel.Silver)
        );
    }

    private Component createSponsorSection(@NotNull final SponsorLevel sponsorLevel) {
        final var section = new Section(new H3(sponsorLevel.getLiteral()));
        section.addClassName("level-%s".formatted(URLUtil.createReadableUrl(sponsorLevel.getLiteral())));
        databaseService.getActiveSponsors(sponsorLevel)
                .map(this::createSponsorArticle)
                .forEach(section::add);
        return section;
    }

    private Component createSponsorArticle(@NotNull final SponsorEntity sponsorEntity) {
        final var sponsor = new Article();

        final var logoHint = "Go to %s website".formatted(sponsorEntity.name());
        final var logo = new Div(new Anchor(sponsorEntity.website(), new Image(sponsorEntity.logo(), logoHint)));
        logo.addClassName("sponsor-logo");
        logo.setTitle(logoHint);
        sponsor.add(logo);
        if (sponsorEntity.level().equals(SponsorLevel.Platinum) || sponsorEntity.level().equals(SponsorLevel.Gold)) {
            sponsor.add(new H4(sponsorEntity.name()));
            sponsor.add(new Html("<div>%s</div>".formatted(sponsorEntity.description())));
        }
        sponsor.add(new Hr());

        return sponsor;
    }

}
