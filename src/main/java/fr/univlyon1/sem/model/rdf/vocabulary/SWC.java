/**
 * @author: Li Ding (http://www.cs.rpi.edu/~dingl )
 */
package fr.univlyon1.sem.model.rdf.vocabulary;


import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * Ontology information
 * label:  Semantic Web Conference Ontology
 * comment:   n/a
 */
public class SWC {

    protected static final String NS = "http://data.semanticweb.org/ns/swc/ontology#";

    public static final String getURI() {
        return NS;
    }

    // Class (88)
    public final static Resource ConferenceDinner = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ConferenceDinner");

    public final static Resource PanelEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PanelEvent");

    public final static Resource Delegate = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Delegate");

    public final static Resource FreeTimeBreak = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#FreeTimeBreak");

    public final static Resource Webmaster = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Webmaster");

    public final static Resource SWChallengeChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SWChallengeChair");

    public final static Resource PosterPresentation = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PosterPresentation");

    public final static Resource PaperSession = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PaperSession");

    public final static Resource ResearchTrack = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ResearchTrack");

    public final static Resource KeynoteTalk = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#KeynoteTalk");

    public final static Resource ConferenceClosingEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ConferenceClosingEvent");

    public final static Resource SponsorshipChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SponsorshipChair");

    public final static Resource DrinkingPlace = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#DrinkingPlace");

    public final static Resource Artefact = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Artefact");

    public final static Resource Programme = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Programme");

    public final static Resource ProgrammeChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ProgrammeChair");

    public final static Resource AcademicEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#AcademicEvent");

    public final static Resource MealEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#MealEvent");

    public final static Resource EatingPlace = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#EatingPlace");

    public final static Resource CallForPosters = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#CallForPosters");

    public final static Resource SessionChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SessionChair");

    public final static Resource DogfoodTsar = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#DogfoodTsar");

    public final static Resource SubmissionsChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SubmissionsChair");

    public final static Resource PostersChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PostersChair");

    public final static Resource TutorialPresenter = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#TutorialPresenter");

    public final static Resource WorkshopEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#WorkshopEvent");

    public final static Resource MealBreak = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#MealBreak");

    public final static Resource PublicityChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PublicityChair");

    public final static Resource CallForDemos = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#CallForDemos");

    public final static Resource Place = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Place");

    public final static Resource WorkshopsChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#WorkshopsChair");

    public final static Resource CallForParticipation = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#CallForParticipation");

    public final static Resource InvitedPaper = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#InvitedPaper");

    public final static Resource CallForPapers = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#CallForPapers");

    public final static Resource OrganisedEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#OrganisedEvent");

    public final static Resource IndustrialTalk = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#IndustrialTalk");

    public final static Resource SlideSet = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SlideSet");

    public final static Resource SystemDemonstration = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SystemDemonstration");

    public final static Resource Proceedings = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Proceedings");

    public final static Resource ProgrammeCommitteeMember = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ProgrammeCommitteeMember");

    public final static Resource Treasurer = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Treasurer");

    public final static Resource PosterSession = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PosterSession");

    public final static Resource BreakEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#BreakEvent");

    public final static Resource WelcomeTalk = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#WelcomeTalk");

    public final static Resource Presenter = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Presenter");

    public final static Resource DemosChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#DemosChair");

    public final static Resource PaperPresentation = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PaperPresentation");

    public final static Resource MeetingRoomPlace = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#MeetingRoomPlace");

    public final static Resource LocalOrganiser = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#LocalOrganiser");

    public final static Resource ArgumentativeDocument = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ArgumentativeDocument");

    public final static Resource IndustryChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#IndustryChair");

    public final static Resource Poster = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Poster");

    public final static Resource NonAcademicEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#NonAcademicEvent");

    public final static Resource DemoSession = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#DemoSession");

    public final static Resource Paper = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Paper");

    public final static Resource SessionEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SessionEvent");

    public final static Resource CallForProposals = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#CallForProposals");

    public final static Resource TalkEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#TalkEvent");

    public final static Resource TrackChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#TrackChair");

    public final static Resource ConferenceVenuePlace = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ConferenceVenuePlace");

    public final static Resource SocialEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SocialEvent");

    public final static Resource OrganisingCommitteeMember = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#OrganisingCommitteeMember");

    public final static Resource ExhibitionChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ExhibitionChair");

    public final static Resource Role = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Role");

    public final static Resource IndustrialTrack = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#IndustrialTrack");

    public final static Resource WorkshopOrganiser = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#WorkshopOrganiser");

    public final static Resource PhDSymposiumChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PhDSymposiumChair");

    public final static Resource Administrator = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Administrator");

    public final static Resource Chair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Chair");

    public final static Resource ConferenceOpeningEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ConferenceOpeningEvent");

    public final static Resource PrintedProceedingsChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#PrintedProceedingsChair");

    public final static Resource Sponsorship = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Sponsorship");

    public final static Resource TutorialsChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#TutorialsChair");

    public final static Resource CoffeeBreak = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#CoffeeBreak");

    public final static Resource Reception = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Reception");

    public final static Resource ConferenceChair = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ConferenceChair");

    public final static Resource ConferenceEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#ConferenceEvent");

    public final static Resource CommunalPlace = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#CommunalPlace");

    public final static Resource SystemDescription = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#SystemDescription");

    public final static Resource AccommodationPlace = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#AccommodationPlace");

    public final static Resource AdditionalReviewer = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#AdditionalReviewer");

    public final static Resource TrackEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#TrackEvent");

    public final static Resource Reviewer = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Reviewer");

    public final static Resource TutorialEvent = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#TutorialEvent");

    public final static Resource Excursion = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Excursion");

    public final static Resource Tutor = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Tutor");

    public final static Resource Call = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#Call");

    public final static Resource DemoPresentation = ResourceFactory.createResource("http://data.semanticweb.org/ns/swc/ontology#DemoPresentation");

    // Property (35)
    public final static Property hasLocation = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasLocation");

    public final static Property hasCostAmount = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasCostAmount");

    public final static Property relatedToEvent = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#relatedToEvent");

    public final static Property hasSubmissionInstructions = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasSubmissionInstructions");

    public final static Property memberOf = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#memberOf");

    public final static Property hasProgramme = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasProgramme");

    public final static Property isPartOf = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isPartOf");

    public final static Property hasTopic = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasTopic");

    public final static Property uuid = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#uuid");

    public final static Property isRoleAt = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isRoleAt");

    public final static Property hasSponsorship = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasSponsorship");

    public final static Property isTopicOf = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isTopicOf");

    public final static Property hasSubmissionDeadline = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasSubmissionDeadline");

    public final static Property hasCameraReadyDeadline = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasCameraReadyDeadline");

    public final static Property biblioReference = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#biblioReference");

    public final static Property hasMenu = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasMenu");

    public final static Property hasRole = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasRole");

    public final static Property affiliation = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#affiliation");

    public final static Property hasPart = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasPart");

    public final static Property isSubEventOf = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isSubEventOf");

    public final static Property holdsRole = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#holdsRole");

    public final static Property hasCall = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasCall");

    public final static Property attendeeAt = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#attendeeAt");

    public final static Property hasRelatedDocument = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasRelatedDocument");

    public final static Property plansToAttend = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#plansToAttend");

    public final static Property isLocationFor = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isLocationFor");

    public final static Property hasNotificationDeadline = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasNotificationDeadline");

    public final static Property isSuperEventOf = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isSuperEventOf");

    public final static Property hasAttendee = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasAttendee");

    public final static Property forEvent = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#forEvent");

    public final static Property hasRelatedArtefact = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasRelatedArtefact");

    public final static Property hasCostCurrency = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#hasCostCurrency");

    public final static Property heldBy = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#heldBy");

    public final static Property isProviderOf = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isProviderOf");

    public final static Property isProvidedBy = ResourceFactory.createProperty("http://data.semanticweb.org/ns/swc/ontology#isProvidedBy");


    public static String getURI(String name){
        if (name.equals("conference")){
            return ConferenceEvent.getURI();
        }
        else if (name.equals("panel")){
            return PanelEvent.getURI();
        }
        else if (name.equals("keynote")){
            return KeynoteTalk.getURI();
        }
        else if (name.equals("session") || name.equals("sessionevent")){
            return SessionEvent.getURI();
        }
        else if (name.equals("sessionChair")){
            return SessionChair.getURI();
        }
        else if (name.equals("track")){
            return TrackEvent.getURI();
        }
        else if (name.equals("track")){
            return TrackChair.getURI();
        }
        else if (name.equals("publication")){
            return Paper.getURI();
        }
        else if (name.equals("publicationPres")){
            return PaperPresentation.getURI();
        }
        else if (name.equals("poster")){
            return Poster.getURI();
        }
        else if (name.equals("tutorial")){
            return TutorialEvent.getURI();
        }
        else if (name.equals("workshop")){
            return WorkshopEvent.getURI();
        }
        else if (name.equals("memberOf")){
            return memberOf.getURI();
        }
        else if (name.equals("isRoleAt")){
            return isRoleAt.getURI();
        }
        else if (name.equals("hasLocation")){
            return hasLocation.getURI();
        }
        else if (name.equals("isRoleAt")){
            return isRoleAt.getURI();
        }

        else return getURI() + name;
    }

}